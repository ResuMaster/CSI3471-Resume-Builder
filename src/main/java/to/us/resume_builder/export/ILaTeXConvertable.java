package to.us.resume_builder.export;

import to.us.resume_builder.export.template.ResumeTemplate;

public interface ILaTeXConvertable {
    String formatLaTeXString(ResumeTemplate template);
}
