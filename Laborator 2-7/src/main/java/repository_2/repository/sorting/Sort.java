package repository_2.repository.sorting;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Sort{
    private List<Pair<Boolean, String>> fields;

    //Constructor that works with any number of fields - sort ASC
    public Sort(String...args) {
        fields = new ArrayList<>();
        for(String a:args)
            fields.add(new Pair<>(Boolean.TRUE, a));
    }

    //Constructor that works with any number of fields - sort ASC or DESC
    public Sort(Boolean direction, String...args){
        fields = new ArrayList<>();
        for(String a:args)
            fields.add(new Pair<>(direction, a));
    }

    //Operation and: use multiple sorts
    public Sort and(Sort sort){
        for(Pair<Boolean,String> pair:sort.getAll())
            fields.add(new Pair<>(pair.getKey(), pair.getValue()));
        return this;
    }

    public List<Pair<Boolean,String>> getAll(){
        return this.fields;
    }
}

