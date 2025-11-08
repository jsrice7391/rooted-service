const express = require('express');
const {
  getTodayContent,
  getAllContent,
  getContent,
  completeContent,
  getProgressHistory
} = require('../controllers/contentController');
const { protect } = require('../middleware/auth');
const { apiLimiter } = require('../middleware/rateLimiter');

const router = express.Router();

router.get('/today', apiLimiter, protect, getTodayContent);
router.get('/progress/history', apiLimiter, protect, getProgressHistory);
router.get('/:id', apiLimiter, protect, getContent);
router.get('/', apiLimiter, protect, getAllContent);
router.post('/:id/complete', apiLimiter, protect, completeContent);

module.exports = router;
