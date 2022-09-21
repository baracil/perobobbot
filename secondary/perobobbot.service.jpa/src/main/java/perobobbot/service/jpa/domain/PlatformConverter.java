package perobobbot.service.jpa.domain;

import perobobbot.api.data.Platform;

import javax.persistence.AttributeConverter;

public class PlatformConverter implements AttributeConverter<Platform,String> {

    @Override
    public String convertToDatabaseColumn(Platform attribute) {
        return attribute.name();
    }

    @Override
    public Platform convertToEntityAttribute(String dbData) {
        return new Platform(dbData);
    }
}
