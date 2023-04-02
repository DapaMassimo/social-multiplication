package utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


public class BaseUtils {

    private static final Logger logger = LoggerFactory.getLogger(BaseUtils.class);


    public static void copyNonNullProperties(Object source, Object target){
        copyNonNullProperties(source, target, null);
    }

    public static void copyNonNullProperties(Object source, Object target, String... ignoreProperties){
        if (source != null && target != null){
            List<String> ignoreList = ignoreProperties != null ? new ArrayList<>(Arrays.asList(ignoreProperties)) : new ArrayList<>();
            Field[] fields = source.getClass().getDeclaredFields();
            try{
                for(Field field : fields){
                    Object val = new PropertyDescriptor(field.getName(), source.getClass()).getReadMethod().invoke(source);
                    if(val == null && !ignoreList.contains(field.getName()))
                        ignoreList.add(field.getName());
                }
                BeanUtils.copyProperties(source, target, ignoreList.toArray(new String[0]));
            } catch (Exception e){
                logger.debug("Could not copy properties: {}", e.getMessage());
            }
        } else {
            logger.debug("Both source and target object must be not null.");
        }
    }
}
