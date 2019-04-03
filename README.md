# CLaRO
Competency question Language for specifying Requirements for an Ontology

## Introduction
Competency Questions (CQs) for an ontology aim to demarcate the scope of its contents. They are  sparsely used in ontology development, however, likely because there is no tooling and automation for writing them and evaluating them against the ontology, therewith hampering their effective use. Regarding support for authoring CQs, there are a few question templates based on informal analyses of a small number of competency questions, hence, with limited coverage of question types and sentence constructions. No Controlled Natural Language exists to guide the domain experts to author CQs. 

We aim to fill this gap by proposing the \cqcnl~template-based Controlled Natural Language  resource to author CQs. For its design, we exploited a new dataset of 234 TBox-level CQs that had been analysed automatically into 106 patterns, which we analysed on their shape, commonalities, and recurring patterns, which was subsequently 
used to design a template-based CNL, with an additional XML serialisation. The templates also are annotated with a set of human-friendly aspects so that a user may select, e.g., `a CQ template that asks for difference' to narrow down formulating a CQ. 

The CNL was evaluated with a subset of questions from the original dataset and with two sets of newly sourced competency questions. The coverage of \cqcnl, with its 90 main templates and 41 linguistic variants, is about 90\% for unseen questions. \cqcnl~has the potential to facilitate streamlining formalising ontology content requirements and, given that about one third of the competency questions in the test sets turned out to be invalid questions, assist in writing good questions.  
