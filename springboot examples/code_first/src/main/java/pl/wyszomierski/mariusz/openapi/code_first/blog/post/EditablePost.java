package pl.wyszomierski.mariusz.openapi.code_first.blog.post;

import jakarta.validation.constraints.Size;

public record EditablePost(@Size(max = 50) String title, @Size(max = 1000)  String description) {

}
