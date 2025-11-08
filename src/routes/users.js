const express = require('express');
const {
  getNearbyUsers,
  getUserProfile,
  addConnection,
  removeConnection,
  getConnections
} = require('../controllers/userController');
const { protect } = require('../middleware/auth');

const router = express.Router();

router.get('/nearby', protect, getNearbyUsers);
router.get('/connections', protect, getConnections);
router.get('/:id', protect, getUserProfile);
router.post('/connect/:id', protect, addConnection);
router.delete('/connect/:id', protect, removeConnection);

module.exports = router;
