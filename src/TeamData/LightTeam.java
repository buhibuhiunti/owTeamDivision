package TeamData;

public class LightTeam implements Team{
    LightTeam() {

    }
    @Override
    public void newEntry(Member member,InQueRole inQueRole) {
        teamMember.add(member);
        member.inQue(inQueRole);
    }

    @Override
    public int getAverageRate() {
        return averageRate;
    }
}
