package service;

import domain.Department;
import domain.Faculty;
import domain.Student;
import domain.University;

import java.util.List;
//додати університет окрім списків
public record UniversityData( List<Faculty> faculties) {

}
