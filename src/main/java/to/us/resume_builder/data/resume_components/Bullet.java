package to.us.resume_builder.data.resume_components;

import java.util.logging.Logger;

import to.us.resume_builder.business.export_LaTeX.ILaTeXConvertable;
import to.us.resume_builder.business.export_LaTeX.ResumeTemplate;
import to.us.resume_builder.business.util.MiscUtils;

public class Bullet extends ResumeComponent implements ILaTeXConvertable {
    private static Logger LOGGER = Logger.getLogger(Bullet.class.getName());

    /**
     * The string that is displayed in the resume.
     */
    private String text = "";

    /**
     * Creates a bullet constructor with a specific id.
     *
     * @param id The id for this bullet.
     */
    public Bullet(String id) {
        super(id);
    }

    /**
     * Facilitates cloning bullets
     * 
     * @param bullet
     */
    public Bullet(Bullet bullet) {
        super(bullet);
        this.text = bullet.text;
    }

    /**
     * Get the current String text for this instance.
     *
     * @return The current string text.
     */
    public String getText() {
        return text;
    }

    /**
     * Set the value of text for this instance.
     *
     * @param text The String to set text to.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get the result of serializing this object using the specified template.
     *
     * @param template The template to format this object with.
     *
     * @return A String representing the object in the LaTeX template.
     * @author Matthew McCaskill
     */
    @Override
    public String formatLaTeXString(ResumeTemplate template) {
        return text != null && text.length() > 0 ? template.getFieldTemplate()
            .replaceVariable("content", MiscUtils.escapeLaTeX(this.text))
            .toString(() -> LOGGER.info("Generated LaTeX for bullet \"" + this.text + "\".")) : "";
    }

    @Override
    public ResumeComponent clone() {
        return new Bullet(this);
    }
}
