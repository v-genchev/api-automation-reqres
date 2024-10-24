package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class UserList extends ObjectList<User> {

}
