package ai.maum.mcl.skins.api.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// public class MemberSearch {
//     private String id;
//     private String name;
//     private String birthyear;
//     private String sex;
//     private Integer age;
//     private String concern1;
//     private String concern2;
//     private Integer visitNum;

//     public MemberSearch(String memberInfo, String consultInfo) {
//         this.id = id;
//         this.name = name;
//         this.sex = sex;
//         this.age = age;
//         this.concern1 = concern1;
//         this.concern2 = concern2;
//         this.visitNum = visitNum;
//         this.birthyear = birthyear;

//     }

//     public MemberSearch() {
//     }

// }
public class MemberSearch {
    private List<Map<String, Object>> members;

    public MemberSearch(List<Map<String, Object>> members) {
        this.members = members;
    }

    public List<Map<String, Object>> getMembers() {
        return members;
    }

    public void setMembers(List<Map<String, Object>> members) {
        this.members = members;
    }
}

