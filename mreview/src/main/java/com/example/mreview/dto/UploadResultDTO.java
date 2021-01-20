package com.example.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 파일 업로드 결과데이터
 * - 브라우저에 결과데이터 전송하기 위해 사용
 *      - 그래야 다음 화면 보여줄 수 있음
 * - 브라우저에서 필요한 정보
 *      - 업로드된 파일의 원래이름
 *      - 파일의 UUID 값
 *      - 업로드된 파일의 저장경로
 * - 브라우저에서 간단히 처리할수 있도록 DTO로 처리
 */
@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
    private String fileName;
    private String uuid;
    private String folderPath;

    // 전체경로
    public String getImageURL() {
        try {
            return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    // 섬네일 경로
    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(folderPath+"/s_"+uuid+"_"+fileName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
