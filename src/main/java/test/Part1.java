package test;

import com.google.common.collect.Ordering;
import model.Address;
import model.Person;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;



public class Part1 {

    public static void main(String [] args) {
        List<Person> persons = Arrays.asList(
                new Person("A", 15, new Address("Montreal")),
                new Person("B", 24, new Address("Sherbrooke")),
                new Person("C", 62, new Address("Montreal")),
                new Person("D", 8, new Address("Montreal")),
                new Person("E", 90, new Address("Quebec")),
                new Person("F", 34, new Address("Granby")),
                new Person("G", 21, new Address("Montreal"))
        );

        printStatus(persons);
		
		 /*
		  Should output:
		    D is young [8]
		    A is young [15]
		    G is young [21]
		    B is not that young [24]
		    F is not that young [34]
		    C is not that young [62]
		    E is not that young [90]
		   */
        Map<String, Integer> cs = getCityStats(persons);
				  /*
				    The following should print:
				    Quebec -> 1
				    Granby -> 1
				    Montreal -> 4
				    Sherbrooke -> 1
				  */
        assert cs != null;
        for (Map.Entry<String, Integer> entry : cs.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }


    private static void printStatus(List<Person> persons) {
        // TODO: sort List persons by person's age
        persons.sort(Comparator.comparingInt(Person::getAge));

        String ageGroup;
        for (Person person: persons){
            if (person.getAge()<24) {
                ageGroup = " is young ";
            }
            else{
                ageGroup = " is not that young ";
            }
            System.out.println(person.getName()+ageGroup+"["+person.getAge()+"]");
        }
    }

    private static Map<String, Integer> getCityStats(List<Person> persons) {
        // TODO: ITERATE the List "persons", check each person's city, then count the city name up.
        Map<String, Integer> cityMap = new HashMap<String,Integer>();
        for (Person person: persons) {
            Integer count;
            if (cityMap.containsKey(person.getAddress().getCity())){
                count = cityMap.get(person.getAddress().getCity())+1;
                cityMap.put(person.getAddress().getCity(), count);

            }
            else{
                cityMap.put(person.getAddress().getCity(), 1);
            }

        }
        return cityMap;
    }
}
