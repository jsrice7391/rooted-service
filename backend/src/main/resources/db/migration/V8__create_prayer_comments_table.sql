-- Create prayer comments table (words of encouragement)
CREATE TABLE prayer_comments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    prayer_id UUID NOT NULL REFERENCES prayers(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create index on prayer_id for faster lookups
CREATE INDEX idx_prayer_comments_prayer_id ON prayer_comments(prayer_id);

-- Create index on user_id for faster lookups
CREATE INDEX idx_prayer_comments_user_id ON prayer_comments(user_id);

-- Create index on created_at for chronological ordering
CREATE INDEX idx_prayer_comments_created_at ON prayer_comments(created_at DESC);

-- Trigger for automatic timestamp updates on prayer_comments
CREATE TRIGGER update_prayer_comments_updated_at
    BEFORE UPDATE ON prayer_comments
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
