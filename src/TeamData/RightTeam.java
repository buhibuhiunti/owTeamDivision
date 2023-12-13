package TeamData;

public class RightTeam extends Team{
    RightTeam(int maxPeople, int tankPeople, int damagePeople, int supportPeople) {
        super(maxPeople,tankPeople,damagePeople,supportPeople);
    }
    RightTeam(Member member, InQueRole inQueRole, int maxPeople, int tankMaxPeople, int damageMaxPeople, int supportMaxPeople){
        super(maxPeople,tankMaxPeople,damageMaxPeople,supportMaxPeople);
        newEntry(member,inQueRole);
    }
}
