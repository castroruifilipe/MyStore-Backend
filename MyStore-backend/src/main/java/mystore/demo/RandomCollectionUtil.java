package mystore.demo;

import com.github.javafaker.Faker;
import com.github.javafaker.Number;

import java.util.*;

public class RandomCollectionUtil {

    public static <T> T choice(Collection<T> collection){
        Number number = new Faker().number();
        List<T> list = new ArrayList<>(collection);
        int listSize = list.size();
        int randPosition = number.numberBetween(0,listSize-1);
        return list.get(randPosition);
    }

    public static <T> Set<T> choices(Collection<T> collection, int k){
        Number number = new Faker().number();

        List<T> itemPool = new ArrayList<>(collection);
        Set<T> res = new HashSet<>();

        int listSize = itemPool.size();

        for(int i=0; i<k; i++){
            int randPosition = number.numberBetween(0,listSize-1);
            T item = itemPool.get(randPosition);
            res.add(item);
        }
        return res;
    }

    public static <T> Set<T> sample(Collection<T> collection, int k){
        Number number = new Faker().number();

        List<T> itemPool = new ArrayList<>(collection);
        Set<T> res = new HashSet<>();

        if(collection.size() < k){
            k = collection.size();
        }

        for(int i=0; i<k; i++){
            int listSize = itemPool.size();
            int randPosition = number.numberBetween(0,listSize-1);
            T item = itemPool.get(randPosition);
            res.add(item);
            itemPool.remove(randPosition);
        }
        return res;
    }

}
