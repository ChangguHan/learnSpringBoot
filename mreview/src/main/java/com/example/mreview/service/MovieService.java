package com.example.mreview.service;

import com.example.mreview.dto.*;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDTO movieDTO);

    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO);

    // dtoToEntity : Movie객체뿐 아니라, MovieImage객체도 같이 처리, 그래서 map 사용
    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                            .mno(movieDTO.getMno())
                            .title(movieDTO.getTitle())
                            .build();

        entityMap.put("movie",movie);

        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        //MovieImageDTO 처리
        if(imageDTOList != null && imageDTOList.size() > 0) {
            List<MovieImage> movieImageList = imageDTOList.stream().map(movieImageDTO -> {
                MovieImage movieImage = MovieImage.builder()
                                                    .inum(movieImageDTO.getInum())
                                                    .path(movieImageDTO.getPath())
                                                    .imgName(movieImageDTO.getImgName())
                                                    .uuid(movieImageDTO.getUuid())
                                                    .movie(movie)
                                                    .build();
                return movieImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList",movieImageList);
        }

        return entityMap;

    }

    /**
     *
     * @param movie : entity
     * @param movieImages : entity List, 조회화면에서 MovieImage 처리하기 위해 여러개 받음
     * @param avg : double의 평점 평균
     * @param reviewCnt : 리뷰개수
     * @return
     */
    default MovieDTO entitiesToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
        MovieDTO movieDTO = MovieDTO.builder()
                                    .mno(movie.getMno())
                                    .title(movie.getTitle())
                                    .regDate(movie.getRegDate())
                                    .modDate(movie.getModDate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .inum(movieImage.getInum())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;

    }

    // 영화번호 이용해서 MovieDTO 가져옴
    MovieDTO getMovie(Long mno);

    void modify(MovieDTO dto);

    void remove(Long mno);

}
