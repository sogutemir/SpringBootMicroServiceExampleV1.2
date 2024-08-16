package org.work.notificationservice.model.mapper;

import org.springframework.beans.BeanUtils;
import org.work.notificationservice.model.dto.NotificationDto;
import org.work.notificationservice.model.entity.Notification;

import java.util.Objects;

public class NotificationMapper extends BaseMapper<Notification, NotificationDto> {

    @Override
    public Notification convertToEntity(NotificationDto dto, Object... args) {

        Notification notification = new Notification();
        if(!Objects.isNull(dto)){
            BeanUtils.copyProperties(dto, notification);
        }
        return notification;
    }

    @Override
    public NotificationDto convertToDto(Notification entity, Object... args) {

        NotificationDto notificationDto = new NotificationDto();
        if(!Objects.isNull(entity)) {
            BeanUtils.copyProperties(entity, notificationDto);
        }
        return notificationDto;    }

}