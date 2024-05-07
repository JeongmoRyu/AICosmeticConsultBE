package ai.maum.mcl.skins.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 응답 정보
 * 코드 형식은 Fxxx로 순차적으로 부여한다.
 * @author baekgol
 */
@Getter
@RequiredArgsConstructor
public enum ResponseMeta implements CodeInfo {
    SUCCESS("F000", "성공적으로 수행하였습니다."),
    FAILURE("F001", "오류가 발생했습니다.");
//    UNAUTHORIZED("F002", "인증되지 않은 사용자입니다."),
//    ACCESS_DENIED("F003", "권한이 존재하지 않습니다."),
//    FILE_UPLOAD_ERROR("F004", "파일 업로드 중 오류가 발생했습니다."),
//    FILE_DELETE_ERROR("F005", "파일 삭제 중 오류가 발생했습니다."),
//    PARAM_WRONG("F006", "요청 파라미터가 존재하지 않거나 잘못되었습니다."),
//    UNAUTHORIZED_DOCUMENT("F007", "API 문서 접근이 인증되지 않은 사용자입니다."),
//    WRONG_DOCUMENT_USER("F008", "잘못된 API 문서 사용자입니다."),
//    ALREADY_LOGIN_DOCUMENT_USER("F009", "이미 로그인한 API 문서 사용자입니다."),
//    SSE_EMITTER_NOT_EXIST("F010", "존재하지 않는 Emitter입니다."),
//    SSE_EMITTER_ID_ALREADY_EXIST("F011", "이미 존재하는 ID입니다."),
//    SSE_EMITTER_CLIENT_ID_ALREADY_EXIST("F012", "이미 존재하는 클라이언트 ID입니다.");

    private final String code;
    private final String message;
}
