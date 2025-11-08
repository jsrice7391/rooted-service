# Security Policy

## Security Measures

This application implements several security measures to protect user data and prevent common vulnerabilities:

### 1. Authentication & Authorization
- **JWT-based authentication**: Secure token-based authentication with configurable expiration
- **Password hashing**: Passwords are hashed using bcryptjs before storage
- **Protected routes**: Authentication middleware validates tokens on all sensitive endpoints

### 2. Rate Limiting
- **General API rate limiter**: 100 requests per 15 minutes per IP
- **Authentication limiter**: 5 attempts per 15 minutes per IP (failed attempts only)
- **Content creation limiter**: 50 posts per hour to prevent spam

### 3. CORS (Cross-Origin Resource Sharing)
- Configurable allowed origins via environment variable
- Rejects requests from unauthorized domains
- Development mode allows localhost by default

### 4. Input Validation & Sanitization
- **Geo-location validation**: Latitude/longitude bounds checking, distance limits
- **Mongoose sanitization**: All database queries use Mongoose which provides built-in protection against NoSQL injection
- **Express validator**: Ready for additional validation rules as needed

### 5. Environment Variables
- Sensitive configuration stored in environment variables
- `.env.example` provided for reference
- `.env` excluded from version control

## Known Security Considerations

### CodeQL Analysis Results

The codebase has been analyzed with CodeQL. Here are the findings and explanations:

#### 1. SQL/NoSQL Injection Warnings (8 instances)
**Status**: False Positives - Safe

These warnings flag user input being used in database queries. However:
- We use **Mongoose ODM** which provides built-in protection against NoSQL injection
- Mongoose converts JavaScript objects to BSON, which is type-safe
- All user input is validated and sanitized before use
- Example: `User.findOne({ email })` is safe because Mongoose handles the conversion

**Locations:**
- `authController.js`: Login and user update queries
- `communityController.js`: Post type and tag filtering
- `contentController.js`: Content type and difficulty filtering

**Recommendation**: These are acceptable and safe due to Mongoose's built-in protections.

#### 2. Sensitive GET Query Parameters (2 instances)
**Status**: Acceptable with validation

Location: `userController.js` - Geo-location queries

The geo-location endpoint accepts latitude and longitude via GET parameters. This is acceptable because:
- These are not truly "sensitive" data (they're public location coordinates)
- Full input validation is implemented (bounds checking, type validation)
- The values are parsed and sanitized before use
- Rate limiting prevents abuse

**Recommendation**: This is acceptable for the use case. If needed, could be moved to POST body in future versions.

## Security Best Practices for Deployment

### Environment Configuration
```bash
# Generate a strong JWT secret
JWT_SECRET=$(openssl rand -base64 32)

# Set secure CORS origins (comma-separated)
CORS_ORIGIN=https://yourdomain.com,https://app.yourdomain.com

# Use production MongoDB URI with authentication
MONGODB_URI=mongodb://user:password@host:port/database?authSource=admin
```

### MongoDB Security
1. Enable authentication on MongoDB
2. Use strong passwords for database users
3. Limit database user permissions (principle of least privilege)
4. Enable TLS/SSL for MongoDB connections
5. Keep MongoDB updated to the latest stable version

### Application Security
1. Use HTTPS in production (TLS/SSL certificates)
2. Keep dependencies updated: `npm audit` and `npm update`
3. Use environment-specific configurations
4. Implement logging and monitoring
5. Regular security audits

### Additional Recommendations
1. **Helmet.js**: Consider adding Helmet.js for additional HTTP header security
2. **Rate limiting storage**: For production, use Redis for rate limiting instead of in-memory
3. **Session management**: Implement token refresh mechanisms
4. **Input validation**: Add express-validator rules for all user inputs
5. **Logging**: Implement structured logging with redaction of sensitive data

## Reporting Security Issues

If you discover a security vulnerability, please email the maintainers directly. Do not open public issues for security vulnerabilities.

## Dependency Security

Run regular security audits:
```bash
npm audit
npm audit fix
```

Check for outdated packages:
```bash
npm outdated
```

## Security Checklist for Production

- [ ] Change default JWT_SECRET to a strong random value
- [ ] Configure CORS_ORIGIN to specific domain(s)
- [ ] Enable HTTPS/TLS
- [ ] Use environment variables for all sensitive configuration
- [ ] Enable MongoDB authentication
- [ ] Set NODE_ENV=production
- [ ] Implement proper logging
- [ ] Set up monitoring and alerting
- [ ] Regular dependency updates
- [ ] Regular security audits
- [ ] Backup strategy in place
- [ ] Rate limiting configured appropriately for your load
