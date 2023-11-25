package TeamData;

public class LeftTeam implements Team{
    LeftTeam() {

    }
    LeftTeam(Member member,InQueRole inQueRole){
        teamMember.add(member);
        member.inQue(inQueRole);
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
