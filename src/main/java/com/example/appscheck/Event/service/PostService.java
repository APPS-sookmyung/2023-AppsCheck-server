package com.example.appscheck.Event.service;

import com.example.appscheck.Auth.TokenDto;
import com.example.appscheck.Event.domain.entity.Event;
import com.example.appscheck.Event.domain.repository.EventRepository;
import com.example.appscheck.Event.dto.PostDeleteDto;
import com.example.appscheck.Event.dto.PostRequestDto;
import com.example.appscheck.Event.dto.PostResponseDto;
import com.example.appscheck.Member.domain.Member;
import com.example.appscheck.Member.dto.DeleteMemberRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final EventRepository eventRepository;

    @Transactional
    public PostResponseDto postSave(PostRequestDto postRequestDto) {
        Event event = Event.builder()
                .eventTime(postRequestDto.getEventTime())
                .eventPlace(postRequestDto.getEventPlace())
                .eventDetail(postRequestDto.getEventDetail())
                .build();

        eventRepository.save(event);

        return PostResponseDto.builder()
                .eventId(event.getEventId())
                .eventTime(event.getEventTime())
                .eventPlace(event.getEventPlace())
                .eventDetail(event.getEventDetail())
                .build();

        }
    @Transactional
    public void postDelete(PostDeleteDto postDeleteDto) {
        if ("delete post".equals(postDeleteDto.getDoubleCheck())) {
            Optional<Event> eventOptional = eventRepository.findById(postDeleteDto.getEventId());
            if (eventOptional.isPresent()) {
                Event event = eventOptional.get();
                eventRepository.delete(event);
            } else {
                throw new EntityNotFoundException("Event not found with ID: " + postDeleteDto.getEventId());
            }
        } else {
            throw new IllegalArgumentException("Invalid confirmation string");
        }
    }

    @Transactional
    public Event findById(int eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("해당 이벤트를 찾을 수 없습니다: " + eventId));
    }

    //등록된 모든 이벤트 정보 보내주기
    @Transactional
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


}

