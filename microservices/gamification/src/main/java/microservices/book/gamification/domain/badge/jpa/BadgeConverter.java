package microservices.book.gamification.domain.badge.jpa;

import microservices.book.gamification.domain.badge.Badge;
import microservices.book.gamification.domain.badge.ScoreBadge;
import microservices.book.gamification.domain.badge.SpecialBadge;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BadgeConverter implements AttributeConverter<Badge, String> {

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
        return attribute.toString();
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

        int i = 0;
        boolean found = false;
        SpecialBadge[] specialBadges = SpecialBadge.values();
        while(i < specialBadges.length && !found){
            if(specialBadges[i].toString().equalsIgnoreCase(dbData)){
                found = true;
                badge = specialBadges[i];
            }
            i++;
        }

        if(!found){
            ScoreBadge[] scoreBadges = ScoreBadge.values();
            i = 0;
            while(i < scoreBadges.length && !found){
                if(scoreBadges[i].toString().equalsIgnoreCase(dbData)){
                    found = true;
                    badge = scoreBadges[i];
                }
                i++;
            }
        }

        return badge;
    }
}
