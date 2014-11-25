package ee.test.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProgramList {

    Set<String> set = new HashSet<String>();
    
    
   {
       set.add("1");
       set.add("2");
       set.add("3");
       set.add("4");
    }
    
    public static void main(String[] args) {
        ProgramList p = new ProgramList();
        
        System.out.println(p.set);
        
        Set<String> set2 = p.getAndClearIsQualifyingPlayers();

        System.out.println(p.set);
        System.out.println(set2);
        
        StringBuilder sb = new StringBuilder();
        sb.append("SessionClientService.hasPlayerSessions() opened sessions: [");
        for (String session : set2) {
            sb.append(session).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");

        System.out.println(sb.toString());
    }
    
    public Set<String> getAndClearIsQualifyingPlayers() {
        Set<String> copy = new HashSet<String>(set);
        set.clear();
        return copy;
    }

}
