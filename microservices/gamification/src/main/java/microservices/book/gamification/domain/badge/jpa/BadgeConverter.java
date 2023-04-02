package microservices.book.gamification.domain.badge.jpa;

import microservices.book.gamification.domain.badge.*;
import org.reflections.Reflections;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.reflections.scanners.Scanners.SubTypes;

@Converter
public class BadgeConverter implements AttributeConverter<Badge, String> {

    private final String separator = ";";

    private final String badgePackage = "microservices.book.gamification.domain.badge";

    private static final Logger logger = LoggerFactory.getLogger(BadgeConverter.class);


    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param attribute the entity attribute value to be converted
     * @return the converted data to be stored in the database
     * column
     */
    @Override
    public String convertToDatabaseColumn(Badge attribute) {
        String columnValue = attribute.getClass().getSimpleName() + separator + attribute;
        return columnValue;
    }


    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct <code>dbData</code> type for the corresponding
     * column for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param dbData the data from the database column to be
     *               converted
     * @return the converted value to be stored in the entity
     * attribute
     */
    @Override
    public Badge convertToEntityAttribute(String dbData) {
        Badge badge = null;
        String enumName = dbData.split(separator)[0];
        String attribute = dbData.split(separator)[1];

        Reflections reflections = new Reflections(badgePackage);

        Set<Class<?>> subTypes = reflections.get(SubTypes.of(Badge.class).asClass());

        if(!subTypes.isEmpty()){
            Optional<Class<?>> badgeOptional = subTypes.stream().filter(subType -> {
                return subType.getSimpleName().equalsIgnoreCase(enumName);
            }).findFirst();

            if(badgeOptional.isPresent()){
                badge = Enum.valueOf(badgeOptional.get().asSubclass(Enum.class), attribute);
            } else logger.debug("badgeOptional is not present");
        } else logger.debug("subtype is empty");

        return badge;
    }
}
