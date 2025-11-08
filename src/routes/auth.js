const express = require('express');
const { register, login, getMe, updateDetails } = require('../controllers/authController');
const { protect } = require('../middleware/auth');
const { authLimiter, apiLimiter } = require('../middleware/rateLimiter');

const router = express.Router();

router.post('/register', authLimiter, register);
router.post('/login', authLimiter, login);
router.get('/me', apiLimiter, protect, getMe);
router.put('/updatedetails', apiLimiter, protect, updateDetails);

module.exports = router;
