package TeamData;

import java.util.ArrayList;
import java.util.List;

public class LeftTeam extends Team{
    LeftTeam(int maxPeople, int tankPeople, int damagePeople, int supportPeople) {
        super(maxPeople,tankPeople,damagePeople,supportPeople);
    }
    LeftTeam(Member member, InQueRole inQueRole, int maxPeople, int tankMaxPeople, int damageMaxPeople, int supportMaxPeople){
        super(maxPeople,tankMaxPeople,damageMaxPeople,supportMaxPeople);
        newEntry(member,inQueRole);
    }
}
