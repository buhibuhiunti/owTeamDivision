import TeamData.Block;
import TeamData.LeftTeam;
import TeamData.Member;
import TeamData.RightTeam;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
//ロール１個だけのやつが毎回同じチーム入る
//全ペアに１人ずつ幹事入れる
//一般参加者強引にチームに振り分ける必要ある

public class Main {
    static List<String> config;
    public static void main(String args[]) throws Exception {
        String filePath = "config.txt";
        String textToWrite = "MAX_PEOPLE=5,TANK_MAX_PEOPLE=1,DAMAGE_MAX_PEOPLE=2,SUPPORT_MAX_PEOPLE=2,UPPER_LIMIT_RATE_DIFFERENCE=2000,MATCHING_ADJUSTMENT_RANGE=500";
        while (true) {
            try {
                config = Files.readAllLines(Paths.get(filePath));
                break;
            } catch (IOException e) {
                try {
                    System.out.println("configファイルが見つかりませんでした\n自動作成します\n");
                    File file = new File(filePath);
                    file.createNewFile();
                    FileWriter writer = new FileWriter(filePath, true);
                    writer.write(textToWrite);
                } catch (IOException e2) {
                    throw new Exception("errorCode 101\nconfigファイルの作成に失敗しました");
                }
            }
        }
        List<Member> leaderList = LoadDatasheet.loadDatasheet("leaderList.csv");
        List<Member> memberList = LoadDatasheet.loadDatasheet("memberList.csv");

        int maxPeople = 0;
        String searchWord = "MAX_PEOPLE";
        if(searchSetting(searchWord) != -1) {
            maxPeople = searchSetting(searchWord);
        } else maxPeople = 5;
        int tankPeople = 0;
        searchWord = "TANK_MAX_PEOPLE";
        if(searchSetting(searchWord) != -1) {
            tankPeople = searchSetting(searchWord);
        } else {
            tankPeople = 1;
        }
        int damagePeople = 0;
        searchWord = "DAMAGE_MAX_PEOPLE";
        if(searchSetting(searchWord) != -1) {
            damagePeople = searchSetting(searchWord);
        } else {
            damagePeople = 2;
        }
        int supportPeople = 0;
        searchWord = "SUPPORT_MAX_PEOPLE";
        if(searchSetting(searchWord) != -1) {
            supportPeople = searchSetting(searchWord);
        } else {
            supportPeople = 2;
        }
        int upperLimitRateDifference = 0;
        searchWord = "UPPER_LIMIT_RATE_DIFFERENCE";
        if(searchSetting(searchWord) != -1) {
            upperLimitRateDifference = searchSetting(searchWord);
        } else {
            upperLimitRateDifference = 2000;
        }
        int matchingAdjustmentRange = 0;
        searchWord = "MATCHING_ADJUSTMENT_RANGE";
        if(searchSetting(searchWord) != -1) {
            matchingAdjustmentRange = searchSetting(searchWord);
        } else {
            matchingAdjustmentRange = 500;
        }
        int minNumTeamsFormed = memberList.size() + leaderList.size();
        Block block = new Block(maxPeople, tankPeople, damagePeople, supportPeople, upperLimitRateDifference, matchingAdjustmentRange);
        Block cloneBlock;
        int count = 0;
        for(Member member : leaderList) {
            block.leaderInQue(member);
        }
        List<Pair<LeftTeam, RightTeam>> leaderOnlyTeamList = block.cloneList();
        while(true) {
            cloneBlock = new Block(maxPeople,tankPeople,damagePeople,supportPeople,upperLimitRateDifference,matchingAdjustmentRange,leaderOnlyTeamList);
            Collections.shuffle(memberList);
            for (int j = 1; j <= 3; j++) {
                for (Member member : memberList) {
                    int[] desiredRoleOrder = member.getDesiredRoleOrder();
                    int numRoleSelected = 0;
                    for (int i = 0; i < desiredRoleOrder.length; i++) {
                        if (desiredRoleOrder[i] != -1) {
                            numRoleSelected++;
                        }
                    }
                    if (numRoleSelected == j) {
                        cloneBlock.inQue(member);
                    }
                }
            }
            if(minNumTeamsFormed == -1) {
                break;
            } else {
                if(cloneBlock.numFullTeams() >= minNumTeamsFormed) {
                    break;
                }
                count++;
                if(count % 5000 == 0) {
                    System.out.print(".");
                }
                if(count >= 10000000) {
                    minNumTeamsFormed = minNumTeamsFormed - maxPeople;
                    count = 0;
                }
            }
        }
        System.out.print("\n");
        cloneBlock.printTeamList();
        }
    private static int listSearch(String searchWord) {
        int index = -1;
        for (int i = 0; i < config.size(); i++) {
            if (config.get(i).contains(searchWord)) {
                index = i;
                break;
            }
        }
        return index;
    }
    private static int searchSetting(String searchWord) {
        int index = listSearch(searchWord);
        int wordNum = -1;
        int commaNum = -1;
        try {
            wordNum = config.get(index).indexOf(searchWord);
            commaNum = config.get(index).indexOf(',',wordNum+searchWord.length());
            int num = Integer.parseInt(config.get(index).substring(wordNum+searchWord.length()+1,commaNum));
            return num;
        }catch (Exception e) {
            return -1;
        }
    }

}
