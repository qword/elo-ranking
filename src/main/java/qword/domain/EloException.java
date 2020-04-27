package qword.domain;

import lombok.Value;

@Value
public class EloException extends RuntimeException {
    String message;
}
