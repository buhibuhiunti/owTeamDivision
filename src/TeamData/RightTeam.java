package TeamData;

import java.util.ArrayList;
import java.util.List;

public class RightTeam extends Team{
    RightTeam(int maxPeople, int tankPeople, int damagePeople, int supportPeople) {
        super(maxPeople,tankPeople,damagePeople,supportPeople);
    }
    RightTeam(Member member, InQueRole inQueRole, int maxPeople, int tankMaxPeople, int damageMaxPeople, int supportMaxPeople){
        super(maxPeople,tankMaxPeople,damageMaxPeople,supportMaxPeople);
        newEntry(member,inQueRole);
    }
    private RightTeam(List<Member> teamMember, Team team) {
        super(teamMember,team);
    }
    RightTeam deepCopy(Team team) {
        List<Member> cloneTeamMember = new ArrayList<>();
        for(Member member : this.teamMember) {
            cloneTeamMember.add(member.deepCopy(member));
        }
        return new RightTeam(cloneTeamMember,team);
    }
}
