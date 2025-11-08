-- Create community posts table
CREATE TABLE community_posts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    prayer_id UUID REFERENCES prayers(id) ON DELETE CASCADE,
    likes_count INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create index on user_id for faster lookups
CREATE INDEX idx_community_posts_user_id ON community_posts(user_id);

-- Create index on prayer_id for faster lookups
CREATE INDEX idx_community_posts_prayer_id ON community_posts(prayer_id);

-- Create index on created_at for chronological ordering
CREATE INDEX idx_community_posts_created_at ON community_posts(created_at DESC);
