-- Add organization reference to events table
ALTER TABLE events ADD COLUMN organization_id UUID REFERENCES organizations(id) ON DELETE CASCADE;

-- Make organization_id required for new events (nullable for now to allow migration)
-- UPDATE: We'll handle this in the application layer

-- Create index on organization_id for faster lookups
CREATE INDEX idx_events_organization_id ON events(organization_id);

-- Add created_by user reference to track who created the event
ALTER TABLE events ADD COLUMN created_by UUID REFERENCES users(id) ON DELETE SET NULL;

-- Create index on created_by for faster lookups
CREATE INDEX idx_events_created_by ON events(created_by);

-- Make organization_name nullable since we now have organization reference
ALTER TABLE events ALTER COLUMN organization_name DROP NOT NULL;
