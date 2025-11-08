-- Create organizations table (churches/ministries)
CREATE TABLE organizations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name TEXT NOT NULL,
    description TEXT,
    organization_type TEXT NOT NULL CHECK (organization_type IN ('CHURCH', 'MINISTRY', 'NONPROFIT', 'OTHER')),
    contact_email TEXT,
    contact_phone TEXT,
    website_url TEXT,
    logo_url TEXT,
    address TEXT,
    city TEXT,
    state TEXT,
    zip_code TEXT,
    country TEXT DEFAULT 'USA',
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    admin_user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    is_verified BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create index on admin_user_id for faster lookups
CREATE INDEX idx_organizations_admin_user_id ON organizations(admin_user_id);

-- Create index on organization_type for filtering
CREATE INDEX idx_organizations_type ON organizations(organization_type);

-- Create spatial index on location for geospatial queries
CREATE INDEX idx_organizations_location ON organizations USING GIST (
    ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)
) WHERE latitude IS NOT NULL AND longitude IS NOT NULL;

-- Trigger for automatic timestamp updates on organizations
CREATE TRIGGER update_organizations_updated_at
    BEFORE UPDATE ON organizations
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Create organization members table (users can join organizations)
CREATE TABLE organization_members (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role TEXT NOT NULL DEFAULT 'MEMBER' CHECK (role IN ('ADMIN', 'MODERATOR', 'MEMBER')),
    joined_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    UNIQUE(organization_id, user_id)
);

-- Create index on organization_id for faster lookups
CREATE INDEX idx_organization_members_organization_id ON organization_members(organization_id);

-- Create index on user_id for faster lookups
CREATE INDEX idx_organization_members_user_id ON organization_members(user_id);
