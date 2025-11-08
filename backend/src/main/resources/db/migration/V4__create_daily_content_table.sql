-- Create daily content table
CREATE TABLE daily_content (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    theologian_name TEXT NOT NULL,
    theologian_bio TEXT,
    scripture_reference TEXT,
    reflection_question TEXT,
    publish_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create index on publish_date for faster date-based queries
CREATE INDEX idx_daily_content_publish_date ON daily_content(publish_date);

-- Create unique index to ensure only one content per date
CREATE UNIQUE INDEX idx_daily_content_unique_date ON daily_content(publish_date);
