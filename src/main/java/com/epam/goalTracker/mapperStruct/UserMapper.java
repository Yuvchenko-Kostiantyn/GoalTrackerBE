//package com.epam.goalTracker.mapperStruct;
//
//import com.epam.sportbuddies.dto.request.PersonRequest;
//import com.epam.sportbuddies.dto.response.*;
//import com.epam.sportbuddies.entity.Person;
//import org.mapstruct.Mapper;
//import org.mapstruct.MappingTarget;
//import org.springframework.data.domain.Page;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//
//    Person requestToEntity(PersonRequest dto);
//
//    Person requestToEntity(PersonRequest dto, @MappingTarget Person person);
//
//    PersonRequest toDtoRequest(Person entity);
//
//    PersonResponse toDtoResponse(Person entity);
//
//    OnlinePerson toDtoOnlineResponse(Person person);
//
//    SimplePersonResponse toDtoResponseSimple(Person person);
//
//    PersonResponse toPersonResponse(Person person);
//
//    PersonPublicResponse toPersonPublicResponse(Person person);
//
//    PersonPrivateResponse toPersonProfileResponse(Person person);
//
//    Person requestToPerson(PersonRequest dto);
//
//    PersonPrivateResponse toDtoPersonProfileResponse(Person person);
//
//    Person responseToEntity(PersonResponse dto);
//
//    ChatMessageNotificationResponse toDtoResponseCM(Person entity);
//
//    default Page<SimplePersonResponse> toDtoResponseSimples(Page<Person> personPage) {
//        return personPage.map(this::toDtoResponseSimple);
//    }
//
//    default PageResponse<PersonResponse> toPageResponse(Page<Person> page) {
//        return new PageResponse<>(
//                page.getTotalPages(),
//                page.getTotalElements(),
//                page.get()
//                        .map(this::toDtoResponse)
//                        .collect(Collectors.toList()));
//    }
//
//    default Set<PersonResponse> toSetResponse(Set<Person> people) {
//        return people.stream()
//                .map(this::toDtoResponse)
//                .collect(Collectors.toSet());
//    }
//
//}
