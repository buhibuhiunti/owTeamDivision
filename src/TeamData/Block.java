package TeamData;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class Block implements Cloneable{
    LeftTeam leftTeam;
    RightTeam rightTeam;
    List<Pair<LeftTeam, RightTeam>> teamList = new ArrayList<Pair<LeftTeam, RightTeam>>();
    final int UPPER_LIMIT_RATE_DIFFERENCE;
    final int MATCHING_ADJUSTMENT_RANGE;
    final int MAX_PEOPLE;
    final int TANK_MAX_PEOPLE;
    final int DAMAGE_MAX_PEOPLE;
    final int SUPPORT_MAX_PEOPLE;
    public Block(int maxPeople, int tankMaxPeople, int damageMaxPeople, int supportMaxPeople, int upperLimitRateDifference, int matchingAdjustmentRange) {
        this.MAX_PEOPLE = maxPeople;
        this.TANK_MAX_PEOPLE = tankMaxPeople;
        this.DAMAGE_MAX_PEOPLE = damageMaxPeople;
        this.SUPPORT_MAX_PEOPLE = supportMaxPeople;
        this.leftTeam = new LeftTeam(maxPeople, tankMaxPeople, damageMaxPeople, supportMaxPeople);
        this.rightTeam = new RightTeam(maxPeople, tankMaxPeople, damageMaxPeople, supportMaxPeople);
        Pair<LeftTeam, RightTeam> teamPair = Pair.of(this.leftTeam,this.rightTeam);
        this.teamList.add(teamPair);
        this.UPPER_LIMIT_RATE_DIFFERENCE = upperLimitRateDifference;
        this.MATCHING_ADJUSTMENT_RANGE = matchingAdjustmentRange;
    }
    public Block(int maxPeople, int tankMaxPeople, int damageMaxPeople, int supportMaxPeople, int upperLimitRateDifference, int matchingAdjustmentRange,
                 List<Pair<LeftTeam,RightTeam>> teamList) {
        this.MAX_PEOPLE = maxPeople;
        this.TANK_MAX_PEOPLE = tankMaxPeople;
        this.DAMAGE_MAX_PEOPLE = damageMaxPeople;
        this.SUPPORT_MAX_PEOPLE = supportMaxPeople;
        this.teamList = new ArrayList<>(teamList);
        this.UPPER_LIMIT_RATE_DIFFERENCE = upperLimitRateDifference;
        this.MATCHING_ADJUSTMENT_RANGE = matchingAdjustmentRange;
    }
    public void leaderInQue(Member member) {
        InQueRole inQueRole = InQueRole.NODATA;
        if(member.getDesiredRoleOrder()[0] == 1) {
            inQueRole = InQueRole.TANK;
        }
        if(member.getDesiredRoleOrder()[1] == 1) {
            inQueRole = InQueRole.DAMAGE;
        }
        if(member.getDesiredRoleOrder()[2] == 1) {
            inQueRole = InQueRole.SUPPORT;
        }
        newMatch(member,inQueRole);
    }
    public List<Pair<LeftTeam,RightTeam>> cloneList() {
        List<Pair<LeftTeam,RightTeam>> cloneTeamList = new ArrayList<Pair<LeftTeam,RightTeam>>();
        LeftTeam cloneLeftTeam;
        RightTeam cloneRightTeam;
        Pair<LeftTeam,RightTeam> cloneTeamPair;
        for(Pair<LeftTeam,RightTeam> Pair : this.teamList) {
            cloneLeftTeam = Pair.getLeft().deepCopy(Pair.getLeft());
            cloneRightTeam = Pair.getRight().deepCopy(Pair.getRight());
            cloneTeamPair = Pair.of(cloneLeftTeam,cloneRightTeam);
            cloneTeamList.add(cloneTeamPair);
        }
        return cloneTeamList;
    }
    void newMatch(Member member,InQueRole inQueRole){
        this.leftTeam = new LeftTeam(this.MAX_PEOPLE,this.TANK_MAX_PEOPLE,this.DAMAGE_MAX_PEOPLE,this.SUPPORT_MAX_PEOPLE);
        this.rightTeam = new RightTeam(this.MAX_PEOPLE,this.TANK_MAX_PEOPLE,this.DAMAGE_MAX_PEOPLE,this.SUPPORT_MAX_PEOPLE);
        this.leftTeam.newEntry(member,inQueRole);
        Pair<LeftTeam, RightTeam> teamPair = Pair.of(this.leftTeam,this.rightTeam);
        this.teamList.add(teamPair);
    }
    public void inQue(Member member) {
        int[] rateList = member.getRate();
        int numRoleSelected = 0;
        for(int i : rateList) {
            if(i != -1) {
                numRoleSelected++;
            }
        }
        final int AVERAGENUM = 2700;
        if(numRoleSelected != 1) {
            if(rateList[0] != -1) {
                if(rateList[1] != -1 && rateList[2] != -1) {
                    if(Math.abs(rateList[0] - AVERAGENUM) < Math.abs(rateList[1] - AVERAGENUM) &&
                    Math.abs(rateList[0] - AVERAGENUM) < Math.abs(rateList[2] - AVERAGENUM)) {
                        if(!addTeams(member,InQueRole.TANK)) {
                            if(Math.abs(rateList[1] - AVERAGENUM) < Math.abs(rateList[2] - AVERAGENUM)){
                                if(!addTeams(member,InQueRole.DAMAGE)) {
                                    if(!addTeams(member,InQueRole.SUPPORT)) {
                                        newMatch(member,InQueRole.SUPPORT);
                                    }
                                }
                                return ;
                            }
                            if(!addTeams(member,InQueRole.SUPPORT)) {
                                if(!addTeams(member,InQueRole.DAMAGE)) {
                                    newMatch(member,InQueRole.DAMAGE);
                                }
                            }
                        }
                        return ;
                    }
                    if(Math.abs(rateList[1] - AVERAGENUM) < Math.abs(rateList[0] - AVERAGENUM) &&
                    Math.abs(rateList[1] - AVERAGENUM) < Math.abs(rateList[2] - AVERAGENUM)) {
                        if(!addTeams(member,InQueRole.DAMAGE)) {
                            if(rateList[2] != -1) {
                                if(Math.abs(rateList[0] - AVERAGENUM) < Math.abs(rateList[2] - AVERAGENUM)) {
                                    if(!addTeams(member,InQueRole.TANK)) {
                                        if(!addTeams(member,InQueRole.SUPPORT)) {
                                            newMatch(member,InQueRole.SUPPORT);
                                        }
                                    }
                                    return ;
                                }
                                if(!addTeams(member,InQueRole.SUPPORT)) {
                                    if(!addTeams(member,InQueRole.TANK)) {
                                        newMatch(member,InQueRole.TANK);
                                    }
                                }
                            }
                        }
                        return ;
                    }
                    if(Math.abs(rateList[2] - AVERAGENUM) < Math.abs(rateList[0]) - AVERAGENUM &&
                            Math.abs(rateList[2] - AVERAGENUM) < Math.abs(rateList[1] - AVERAGENUM)) {
                        if(!addTeams(member,InQueRole.SUPPORT)) {
                            if(rateList[1] != -1) {
                                if(Math.abs(rateList[0] - AVERAGENUM) < Math.abs(rateList[1] - AVERAGENUM)) {
                                    if(!addTeams(member,InQueRole.TANK)) {
                                        if(!addTeams(member,InQueRole.DAMAGE)) {
                                            newMatch(member,InQueRole.DAMAGE);
                                        }
                                    }
                                    return ;
                                }
                                if(!addTeams(member,InQueRole.DAMAGE)) {
                                    if(!addTeams(member,InQueRole.TANK)) {
                                        newMatch(member,InQueRole.TANK);
                                    }
                                }
                                return ;
                            }
                        }
                        return ;
                    }
                }
                if(rateList[1] != -1) {
                    if(Math.abs(rateList[0] - AVERAGENUM) < Math.abs(rateList[1] - AVERAGENUM)) {
                        if(!addTeams(member,InQueRole.TANK)) {
                            if(!addTeams(member,InQueRole.DAMAGE)) {
                                newMatch(member,InQueRole.DAMAGE);
                            }
                        }
                        return ;
                    }
                    if(!addTeams(member,InQueRole.DAMAGE)) {
                        if(!addTeams(member,InQueRole.TANK)) {
                            newMatch(member,InQueRole.TANK);
                        }
                    }
                    return ;
                }
                if(rateList[2] != -1) {
                    if(Math.abs(rateList[0] - AVERAGENUM) < Math.abs(rateList[2] - AVERAGENUM)) {
                        if(!addTeams(member,InQueRole.TANK)) {
                            if(!addTeams(member,InQueRole.SUPPORT)) {
                                newMatch(member,InQueRole.SUPPORT);
                            }
                        }
                        return ;
                    }
                    if(!addTeams(member,InQueRole.SUPPORT)) {
                        if(!addTeams(member,InQueRole.TANK)) {
                            newMatch(member,InQueRole.TANK);
                        }
                    }
                    return ;
                }
            }
            if(Math.abs(rateList[1] - AVERAGENUM) < Math.abs(rateList[2] - AVERAGENUM)){
                if(!addTeams(member,InQueRole.DAMAGE)) {
                    if(!addTeams(member,InQueRole.SUPPORT)) {
                        newMatch(member,InQueRole.SUPPORT);
                    }
                }
                return ;
            }
            if(!addTeams(member,InQueRole.SUPPORT)) {
                if(!addTeams(member,InQueRole.DAMAGE)) {
                    newMatch(member,InQueRole.DAMAGE);
                }
            }
            return ;
        }
        if(rateList[0] != -1) {
            if(!addTeams(member,InQueRole.TANK)) {
                newMatch(member,InQueRole.TANK);
            }
        }
        if(rateList[1] != -1) {
            if(!addTeams(member,InQueRole.DAMAGE)) {
                newMatch(member,InQueRole.DAMAGE);
            }
        }
        if(rateList[2] != -1) {
            if(!addTeams(member,InQueRole.SUPPORT)) {
                newMatch(member,InQueRole.SUPPORT);
            }
        }
    }
    private boolean addTeams(Member member,InQueRole inQueRole) {
        if(inQueRole == InQueRole.NODATA) {
            new IllegalArgumentException("レートが入力されていない参加者が居ます\n該当ユーザーのバトルタグ：" + member.getBattleTag());
        }
        Team acceptableTeam = null;
        int rateDifference = 10000;
        int matchingScore = -1;
        for(Pair<LeftTeam, RightTeam> TeamPair : this.teamList) {
            matchingScore = searchAcceptableTeams(member,inQueRole,TeamPair.getLeft(),TeamPair.getRight());
            if(matchingScore != -1 && matchingScore % 10000 < rateDifference) {
                rateDifference = matchingScore % 10000;
                if(matchingScore >= 20000) {
                    acceptableTeam = TeamPair.getRight();
                } else if(matchingScore >= 10000) {
                    acceptableTeam = TeamPair.getLeft();
                }
            }
        }
        if(acceptableTeam == null) {
            if(matchingScore != -1) {
                throw new NullPointerException("エラーコード：001\nシステム管理者に連絡してください\n" + member.getBattleTag());
            }
        } else if (rateDifference != 10000) {
            acceptableTeam.newEntry(member,inQueRole);
            return true;
        }
        return false;
    }
    private int searchAcceptableTeams(Member member,InQueRole inQueRole,LeftTeam leftTeam, RightTeam rightTeam) {
        int rate = 0;
        switch (inQueRole) {
            case TANK:
                rate = member.getRate()[0];
                break;
            case DAMAGE:
                rate = member.getRate()[1];
                break;
            case SUPPORT:
                rate = member.getRate()[2];
                break;
        }
        if(!leftTeam.roleIsFull(inQueRole) &&
                rightTeam.getAverageRate() + MATCHING_ADJUSTMENT_RANGE >= rate &&
        rightTeam.getAverageRate() - MATCHING_ADJUSTMENT_RANGE <= rate) {
            if(leftTeam.getAverageRate() == -1) {
                return Math.abs(rightTeam.getAverageRate() - rate) + 10000;
            }
            if(leftTeam.getMaxRate() + UPPER_LIMIT_RATE_DIFFERENCE >= rate &&
                    leftTeam.getMaxRate() - UPPER_LIMIT_RATE_DIFFERENCE <= rate &&
                    leftTeam.getMinRate() + UPPER_LIMIT_RATE_DIFFERENCE >= rate &&
                    leftTeam.getMinRate() - UPPER_LIMIT_RATE_DIFFERENCE <= rate) {
                return Math.abs(rightTeam.getAverageRate() - rate) + 10000;
            }
        }
        if(!rightTeam.roleIsFull(inQueRole) &&
        leftTeam.getAverageRate() + MATCHING_ADJUSTMENT_RANGE >= rate &&
        leftTeam.getAverageRate() - MATCHING_ADJUSTMENT_RANGE <= rate) {
            if(rightTeam.getMaxRate() == -1) {
                return Math.abs(leftTeam.getAverageRate() - rate) + 20000;
            }
            if(rightTeam.getMaxRate() + UPPER_LIMIT_RATE_DIFFERENCE >= rate &&
                    rightTeam.getMaxRate() - UPPER_LIMIT_RATE_DIFFERENCE <= rate &&
                    rightTeam.getMinRate() + UPPER_LIMIT_RATE_DIFFERENCE >= rate &&
                    rightTeam.getMinRate() - UPPER_LIMIT_RATE_DIFFERENCE <= rate) {
                return Math.abs(leftTeam.getAverageRate() - rate) + 20000;
            }
        }
        return -1;
    }
    public void printTeamList() {
        int i = 1;
        int count = 0;
        this.teamList.remove(0);
        for(Pair<LeftTeam,RightTeam> Pair : this.teamList) {
            System.out.print("チーム" + i + "平均レート" + Pair.getLeft().getAverageRate());
            if(Pair.getLeft().isFull()) {
                System.out.println("◯");
                count++;
            } else {
                System.out.println("×");
            }
            Pair.getLeft().outputMemberList();
            System.out.print("チーム" + ++i + "平均レート" + Pair.getRight().getAverageRate());
            if(Pair.getRight().isFull()) {
                System.out.println("◯");
                count++;
            } else {
                System.out.println("×");
            }
            Pair.getRight().outputMemberList();
            i = ++i;
        }
        System.out.println("メンバーが埋まったチームの数：" + count + "\nそのメンバーの数：" + count * 5);
    }
    public int numFullTeams() {
        int num = 0;
        for(Pair<LeftTeam,RightTeam> Pair : this.teamList) {
            if(Pair.getLeft().isFull()) {
                num++;
            }
            if(Pair.getRight().isFull()) {
                num++;
            }
        }
        return num*this.MAX_PEOPLE;
    }
}
