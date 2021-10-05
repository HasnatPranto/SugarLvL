package Algorithm;

import java.util.HashMap;
import java.util.Map;

public class Node {
    String attribute,decision;
    Map<String,Node> nodes=new HashMap<>();
    boolean leaf;
}
