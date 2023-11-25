package TeamData;

import java.util.ArrayList;
import java.util.List;

public interface Team {
    List<Member> teamMember = new ArrayList<Member>();
    int averageRate = -1;
    int maxRate = -1;
    int minRate = -1;
    int currentMember = 0;
    int currentTankMember = 0;
    int currentDamageMember = 0;
    int currentSupportMember = 0;
    abstract void newEntry(Member member,InQueRole inQueRole);
    abstract int getAverageRate();

}

