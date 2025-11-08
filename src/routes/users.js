const express = require('express');
const {
  getNearbyUsers,
  getUserProfile,
  addConnection,
  removeConnection,
  getConnections
} = require('../controllers/userController');
const { protect } = require('../middleware/auth');
const { apiLimiter } = require('../middleware/rateLimiter');

const router = express.Router();

router.get('/nearby', apiLimiter, protect, getNearbyUsers);
router.get('/connections', apiLimiter, protect, getConnections);
router.get('/:id', apiLimiter, protect, getUserProfile);
router.post('/connect/:id', apiLimiter, protect, addConnection);
router.delete('/connect/:id', apiLimiter, protect, removeConnection);

module.exports = router;
