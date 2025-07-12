package com.dangminhphuc.dev.beans.advance.annotation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("primaryBean")
public class PrimaryBean extends SimpleBean2nd {
}
