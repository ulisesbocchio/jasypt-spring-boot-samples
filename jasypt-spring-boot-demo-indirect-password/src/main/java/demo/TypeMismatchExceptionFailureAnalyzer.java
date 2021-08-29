package demo;

import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeMismatchExceptionFailureAnalyzer extends AbstractFailureAnalyzer<TypeMismatchException> implements EnvironmentAware {

    private ConfigurableEnvironment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, TypeMismatchException cause) {
        String name = cause.getPropertyName() == null ? "secret2.property" : cause.getPropertyName();
        List<Descriptor> descriptors = getDescriptors(name);
        if (descriptors.isEmpty()) {
            return null;
        }
        StringBuilder description = new StringBuilder();
        appendDetails(description, name, descriptors);
        appendReason(description, cause);
        appendAdditionalProperties(description, descriptors);
        return new FailureAnalysis(description.toString(), getAction(cause), cause);
    }

    private List<Descriptor> getDescriptors(String propertyName) {
        return getPropertySources().filter((source) -> source.containsProperty(propertyName))
                .map((source) -> Descriptor.get(source, propertyName)).collect(Collectors.toList());
    }

    private Stream<PropertySource<?>> getPropertySources() {
        if (this.environment == null) {
            return Stream.empty();
        }
        return this.environment.getPropertySources().stream()
                .filter((source) -> !ConfigurationPropertySources.isAttachedConfigurationPropertySource(source));
    }

    private void appendDetails(StringBuilder message, String name,
                               List<Descriptor> descriptors) {
        Descriptor mainDescriptor = descriptors.get(0);
        message.append("Invalid value '").append(mainDescriptor.getValue()).append("' for configuration property '");
        message.append(name).append("'");
        mainDescriptor.appendOrigin(message);
        message.append(".");
    }

    private void appendReason(StringBuilder message, TypeMismatchException cause) {
        if (StringUtils.hasText(cause.getMessage())) {
            message.append(String.format(" Validation failed for the following reason:%n%n"));
            message.append(cause.getMessage());
        }
        else {
            message.append(" No reason was provided.");
        }
    }

    private void appendAdditionalProperties(StringBuilder message, List<Descriptor> descriptors) {
        List<Descriptor> others = descriptors.subList(1, descriptors.size());
        if (!others.isEmpty()) {
            message.append(
                    String.format("%n%nAdditionally, this property is also set in the following property %s:%n%n",
                            (others.size() > 1) ? "sources" : "source"));
            for (Descriptor other : others) {
                message.append("\t- In '").append(other.getPropertySource()).append("'");
                message.append(" with the value '").append(other.getValue()).append("'");
                other.appendOrigin(message);
                message.append(String.format(".%n"));
            }
        }
    }

    private String getAction(TypeMismatchException cause) {
        StringBuilder action = new StringBuilder();
        action.append("Review the value of the property");
        if (cause != null && cause.getRequiredType() != null) {
            action.append(", it should be ");
            action.append(cause.getRequiredType().getName());
        }
        action.append(".");
        return action.toString();
    }

    private static final class Descriptor {

        private final String propertySource;

        private final Object value;

        private final Origin origin;

        private Descriptor(String propertySource, Object value, Origin origin) {
            this.propertySource = propertySource;
            this.value = value;
            this.origin = origin;
        }

        String getPropertySource() {
            return this.propertySource;
        }

        Object getValue() {
            return this.value;
        }

        void appendOrigin(StringBuilder message) {
            if (this.origin != null) {
                message.append(" (originating from '").append(this.origin).append("')");
            }
        }

        static TypeMismatchExceptionFailureAnalyzer.Descriptor get(PropertySource<?> source, String propertyName) {
            Object value = source.getProperty(propertyName);
            Origin origin = OriginLookup.getOrigin(source, propertyName);
            return new TypeMismatchExceptionFailureAnalyzer.Descriptor(source.getName(), value, origin);
        }

    }
}
