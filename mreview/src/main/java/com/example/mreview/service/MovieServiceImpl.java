package com.example.mreview.service;

import com.example.mreview.dto.MovieDTO;
import com.example.mreview.dto.MovieImageDTO;
import com.example.mreview.dto.PageRequestDTO;
import com.example.mreview.dto.PageResultDTO;
import com.example.mreview.entity.Movie;
import com.example.mreview.entity.MovieImage;
import com.example.mreview.repository.MovieImageRepository;
import com.example.mreview.repository.MovieRepository;
import com.example.mreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);
        movieImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });
        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie)arr[0],
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double)arr[2],
                (Long)arr[3]
        ));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        // Movie 엔티티는 가장 앞에 존재
        // 모든 Row가 동일한 값 가짐
        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
    }

    // 수정
    @Override
    public void modify(MovieDTO dto) {
        // 제목만 업데이트
        Optional<Movie> result = movieRepository.findById(dto.getMno());

        // 변한 imageDTOList
        List<MovieImageDTO> changedImgDTOList = dto.getImageDTOList();
        // 기존 imageDTOList
        List<MovieImageDTO> originImgDTOList = getMovie(dto.getMno()).getImageDTOList();

        if(result.isPresent()) {
            Movie entity = result.get();
            entity.changeTitle(dto.getTitle());
            movieRepository.save(entity);

            List<MovieImageDTO> deleteImgDTOList = new ArrayList<>();
            List<MovieImageDTO> addImgDTOList = new ArrayList<>();

            // 추가할것 먼저
            for(int j=0; j<changedImgDTOList.size(); j++) {
                MovieImageDTO cMovieImage = changedImgDTOList.get(j);

                if(cMovieImage.getUuid() == null)  continue;
                Boolean sw = false;
                for(int i=0; i<originImgDTOList.size(); i++) {
                    MovieImageDTO oMovieImage = originImgDTOList.get(i);

                    if(cMovieImage.getUuid().equals(oMovieImage.getUuid())) {
                        sw = true;
                        break;
                    }
                }
                // 없으면 추가해줘야하는 곳에 추가
                if(sw == false) {
                    addImgDTOList.add(cMovieImage);
                }
            }


            // 추가할것 먼저
            for(int j=0; j<originImgDTOList.size(); j++) {
                MovieImageDTO oMovieImage = originImgDTOList.get(j);

                Boolean sw = false;
                for(int i=0; i<changedImgDTOList.size(); i++) {
                    MovieImageDTO cMovieImage = changedImgDTOList.get(i);
                    if(cMovieImage.getUuid() == null) continue;
                    if(cMovieImage.getUuid().equals(oMovieImage.getUuid())) {
                        sw = true;
                        break;
                    }
                }
                // 없으면 추가해줘야하는 곳에 추가
                if(sw == false) {
                    deleteImgDTOList.add(oMovieImage);
                }
            }


            // 삭제할것
            for(int j=0; j<originImgDTOList.size(); j++) {
                MovieImageDTO oMovieImage = originImgDTOList.get(j);

            }

            deleteImgDTOList.forEach(movieImageDTO -> {
                // 엔티티 얻어서 제거해줘야함
//                MovieImage em = MovieImage.builder()
//                        .path(movieImageDTO.getPath())
//                        .imgName(movieImageDTO.getImgName())
//                        .uuid(movieImageDTO.getUuid())
//                        .movie(entity)
//                        .build();

                imageRepository.deleteById(movieImageDTO.getInum());
            });

            addImgDTOList.forEach(movieImageDTO -> {
                // 엔티티 얻어서 제거해줘야함
                MovieImage em = MovieImage.builder()
                        .path(movieImageDTO.getPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(entity)
                        .build();

                imageRepository.save(em);
            });

        }
    }


    // 삭제
    @Transactional
    @Override
    public void remove(Long mno) {
        // FK로 참조하는 review, movieimage 먼저 삭제
        getMovie(mno).getImageDTOList().forEach(movieImage -> {
            imageRepository.deleteById(movieImage.getInum());
        });

        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        // Movie 엔티티는 가장 앞에 존재
        // 모든 Row가 동일한 값 가짐
        Movie movie = (Movie) result.get(0)[0];
        reviewRepository.deleteByMovie(movie);

        movieRepository.deleteById(mno);
    }



}
