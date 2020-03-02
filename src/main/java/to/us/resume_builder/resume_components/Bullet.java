package to.us.resume_builder.resume_components;

import to.us.resume_builder.export.ILaTeXConvertable;

public class Bullet extends ResumeComponent implements ILaTeXConvertable {

    /**
     * The string that is displayed in the resume
     */
    private String text;

    /**
     * a bullet constructor with a specific id
     * @param id the id for this instance
     */
    public Bullet(String id){
        super(id);
    }

    /**
     * Get the current String text for this instance
     * @return the current string text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the value of text for this instance
     * @param text the String to set to
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toLaTeXString() {
        return null;
    }
}
