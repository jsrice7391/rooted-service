const express = require('express');
const {
  getTodayContent,
  getAllContent,
  getContent,
  completeContent,
  getProgressHistory
} = require('../controllers/contentController');
const { protect } = require('../middleware/auth');

const router = express.Router();

router.get('/today', protect, getTodayContent);
router.get('/progress/history', protect, getProgressHistory);
router.get('/:id', protect, getContent);
router.get('/', protect, getAllContent);
router.post('/:id/complete', protect, completeContent);

module.exports = router;
