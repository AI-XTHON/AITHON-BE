package com.eduai.summary.domain;

import com.eduai.resource.domain.Resource;
import com.eduai.summary.converter.StringListConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "summaries")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    private String filename;
    private int pages;
    private String model;

    @Convert(converter = StringListConverter.class)
    private List<String> summary5;

    @Convert(converter = StringListConverter.class)
    private List<String> furtherTopics;

    @OneToMany(mappedBy = "summary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Slide> slides = new ArrayList<>();

    @OneToMany(mappedBy = "summary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GlossaryTerm> glossaryTerms = new ArrayList<>();

    @OneToMany(mappedBy = "summary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();


    public Summary(Resource resource, String filename, int pages, String model, List<String> summary5, List<String> furtherTopics) {
        this.resource = resource;
        this.filename = filename;
        this.pages = pages;
        this.model = model;
        this.summary5 = summary5;
        this.furtherTopics = furtherTopics;
    }

    public void addSlide(Slide slide) {
        this.slides.add(slide);
    }

    public void addGlossaryTerm(GlossaryTerm glossaryTerm) {
        this.glossaryTerms.add(glossaryTerm);
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}