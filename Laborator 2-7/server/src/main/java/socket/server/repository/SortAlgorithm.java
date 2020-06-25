package socket.server.repository;

import socket.common.domain.BaseEntity;
import socket.common.domain.validators.BookStoreException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class SortAlgorithm <ID extends Serializable, T extends BaseEntity<ID>> {
    private List<T> objectList;
    private Sort sort;

    public SortAlgorithm(List<T> objectList, Sort sort) {
        this.objectList = objectList;
        this.sort = sort;
    }

    private int compareInternal(T obj1, T obj2, String fieldName) {
        try {
            Field field1;
            Field field2;
            try{
                field1 = obj1.getClass().getDeclaredField(fieldName);
                field2 = obj2.getClass().getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e){
                field1 = obj1.getClass().getSuperclass().getDeclaredField(fieldName);
                field2 = obj2.getClass().getSuperclass().getDeclaredField(fieldName);
            }

            field1.setAccessible(true);
            String val1 = field1.get(obj1).toString();
            field1.setAccessible(false);

            field2.setAccessible(true);
            String val2 = field2.get(obj2).toString();
            field2.setAccessible(false);

            if(!val1.matches(".*[a-zA-Z].*")){
                //Case when is number
                Long long1 = Long.parseLong(val1);
                Long long2 = Long.parseLong(val2);
                return long1.compareTo(long2);
            }
            return val1.compareTo(val2);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new BookStoreException("INVALID FIELD: " + fieldName + " !");
        }
    }

    private int compareWrapper(T obj1, T obj2){
        return compareMain(obj1, obj2,0);
    }

    private int compareMain(T obj1, T obj2, int index) {
        if (index == objectList.size())
            return 0;

        //sort.getAll() will give us all fields involved
        if (sort.getAll().get(index).getKey() == Boolean.TRUE) {
            if (compareInternal(obj1, obj2, sort.getAll().get(index).getValue()) == 0)
                //Case where objects are sorted well
                return compareMain(obj1, obj2, ++index);
            else
                //Case where objects must be swapped
                return compareInternal(obj1, obj2, sort.getAll().get(index).getValue());
        }

        //Case where sort is DESC
        if (compareInternal(obj1, obj2, sort.getAll().get(index).getValue()) == 0)
            //Case where objects are sorted well
            return compareMain(obj1, obj2, ++index);
        return -compareInternal(obj1, obj2, sort.getAll().get(index).getValue());
    }

    public List<T> sort() {
        return objectList.stream().sorted(this::compareWrapper).collect(Collectors.toList());
    }
}

