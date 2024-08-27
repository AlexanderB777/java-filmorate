package ru.yandex.practicum.filmorate.dao.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.filmorate.dto.ReviewDto;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "useful", source = "useful", defaultValue = "0")
    Review toEntity(ReviewDto dto);

    List<Review> toEntity(List<ReviewDto> dtoList);

    ReviewDto toDto(Review review);

    List<ReviewDto> toDto(List<Review> reviewList);

    default Integer mapUseful(Integer useful) {
        return useful != null ? useful : 0;
    }

}
