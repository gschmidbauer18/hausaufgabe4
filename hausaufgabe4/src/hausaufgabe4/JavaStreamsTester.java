/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hausaufgabe4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author 43676
 */
public class JavaStreamsTester {
    
    public static int getCountEmptyString(List<String> strings)
    {
        return (int) strings.stream().filter(s -> s.equals("")).count();
    }
    
    public static int getCountLength3(List<String> strings)
    {
        return (int) strings.stream().filter(s -> s.length()==3).count();
    }
    
    public static List<String> deleteEmpyStrings(List<String> strings)
    {
        return strings.stream().filter(s -> !s.equals("")).collect(Collectors.toList());
    }
    
    public static String getMergedString(List<String> strings,String seperator)
    {
        return strings.stream().reduce("", (s1,s2) -> s1+seperator+s2);
    }
    
    public static List<Integer> getSquares(List<Integer> numbers)
    {
        return numbers.stream().map(i -> i=i+i).collect(Collectors.toList());
    }
    
    public static int getMax(List<Integer> numbers)
    {
        return numbers.stream().max( (i1,i2) ->Integer.compare(i1, i2)).get();
    }
    
    public static int getMin(List<Integer> numbers)
    {
        return numbers.stream().min( (i1,i2) ->Integer.compare(i1, i2)).get();
    }
    
    public static int getSum(List<Integer> numbers)
    {
        return numbers.stream().reduce(0, (i1,i2)->i1+i2);
    }
    
    public static int getAverage(List<Integer> numbers)
    {
        return (int) numbers.stream().mapToDouble(a -> a).average().getAsDouble();
    }
}
