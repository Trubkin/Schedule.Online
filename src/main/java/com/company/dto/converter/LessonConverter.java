package com.company.dto.converter;

import com.company.dto.LessonDTO;
import com.company.model.Group;
import com.company.model.Lesson;
import org.springframework.stereotype.Service;

@Service
public class LessonConverter extends EntityConverter<Lesson, LessonDTO> {
    @Override
    public LessonDTO convert(Lesson entity) {
        return entity == null ? null : new LessonDTO(
                entity.getId(),
                entity.getName(),
                entity.getRoom(),
                entity.getDatetime(),
                entity.getTeacher(),
                entity.getGroup().getId()
        );
    }

    @Override
    public Lesson restore(LessonDTO dto) {
        if (dto == null) {
            return null;
        }

        Group group = new Group();
        group.setId(dto.getGroupId());
        return new Lesson(
                dto.getName(),
                dto.getRoom(),
                dto.getDatetime(),
                dto.getTeacher(),
                group
        );
    }
}