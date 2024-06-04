package itbank.pethub.dto;

public interface OAuth2Response {

    // naver인지 kakao인지 알려줌
    String getProvider();

    // 제공자의 각각의 유저 번호
    String getProviderId();

    // 유저 이메일
    String getEmail();

    // 유저 이름
    String getName();

}
