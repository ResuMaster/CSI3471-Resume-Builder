package to.us.resume_builder.file;

import to.us.resume_builder.resume_components.Resume;

/**
 * Wrapper class to be used for importing and exporting a Resume.
 *
 * @author Jacob Curtis
 */
public class ResumeFile {
    /**
     * The path to the resume data file
     */
    private transient String filePath;

    /**
     * The metadata associated with the Resume
     */
    private Metadata metadata;

    /**
     * The resume attached to the file
     */
    private Resume resume;

    /**
     * Creates a ResumeFile given metadata and a resume.
     *
     * @param metadata The Metadata to be attached to the ResumeFile
     * @param resume The Resume to be attached to the ResumeFile
     */
    public ResumeFile(Metadata metadata, Resume resume) {
        this.metadata = metadata;
        this.resume = resume;
    }

    /**
     * This method returns the ResumeFile's Metadata.
     *
     * @return The Metadata associated with the ResumeFile
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * This method returns the ResumeFile's Resume.
     *
     * @return The Resume associated with the ResumeFile
     */
    public Resume getResume() {
        return resume;
    }

    /**
     * This method sets the ResumeFile's file path.
     */
    public void setFilePath(String path) {
        this.filePath = path;
    }

    /**
     * This method returns the ResumeFile's filepath.
     *
     * @return The file path associated with the ResumeFile
     */
    public String getFilePath() {
        return filePath;
    }

}