package com.dangminhphuc.dev.beans.advance.annotation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier
@Component("qualifiedBean")
public class QualifiedBean extends SimpleBean2nd {
}
