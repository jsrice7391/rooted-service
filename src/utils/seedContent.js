require('dotenv').config();
const mongoose = require('mongoose');
const DailyContent = require('../models/DailyContent');

const sampleContent = [
  {
    title: 'Understanding Grace',
    content: 'Grace is the unmerited favor of God. It\'s not something we earn or deserve, but rather a gift freely given through Jesus Christ. Today, reflect on how grace has transformed your life and how you can extend that same grace to others.',
    contentType: 'devotional',
    theologian: {
      name: 'John Wesley',
      tradition: 'Methodist',
      bio: 'English cleric, theologian, and evangelist who was a leader of a revival movement within the Church of England.'
    },
    scripture: {
      reference: 'Ephesians 2:8-9',
      text: 'For it is by grace you have been saved, through faith—and this is not from yourselves, it is the gift of God—not by works, so that no one can boast.',
      version: 'NIV'
    },
    reflection: {
      question: 'How has grace changed your perspective on your relationship with God?',
      prompts: [
        'Think of a time when you received grace you didn\'t deserve',
        'Consider how you can show grace to someone today',
        'Reflect on areas where you struggle to accept God\'s grace'
      ]
    },
    tags: ['grace', 'salvation', 'faith'],
    difficulty: 'beginner',
    estimatedReadTime: 5,
    publishDate: new Date()
  },
  {
    title: 'The Power of Prayer',
    content: 'Prayer is not a monologue but a dialogue with God. It\'s an intimate conversation where we can bring our joys, sorrows, questions, and thanksgivings. Through prayer, we align our hearts with God\'s will and experience His presence in profound ways.',
    contentType: 'teaching',
    theologian: {
      name: 'Andrew Murray',
      tradition: 'Reformed',
      bio: 'South African writer, teacher, and Christian pastor who emphasized the importance of prayer and communion with God.'
    },
    scripture: {
      reference: '1 Thessalonians 5:16-18',
      text: 'Rejoice always, pray continually, give thanks in all circumstances; for this is God\'s will for you in Christ Jesus.',
      version: 'NIV'
    },
    reflection: {
      question: 'What barriers prevent you from maintaining a consistent prayer life?',
      prompts: [
        'Set aside 10 minutes today for uninterrupted prayer',
        'Write down three things you\'re grateful for',
        'Pray for someone who has hurt you'
      ]
    },
    tags: ['prayer', 'spiritual-discipline', 'communication'],
    difficulty: 'beginner',
    estimatedReadTime: 6,
    publishDate: new Date(Date.now() - 24 * 60 * 60 * 1000) // Yesterday
  },
  {
    title: 'Faith and Doubt',
    content: 'Doubt is not the opposite of faith; it\'s an element of faith. Even the most faithful followers of Christ experienced moments of doubt. What matters is not the absence of doubt, but how we respond to it—by bringing our questions honestly before God and trusting in His faithfulness.',
    contentType: 'reflection',
    theologian: {
      name: 'Paul Tillich',
      tradition: 'Protestant',
      bio: 'German-American theologian and philosopher who emphasized the relationship between faith and doubt.'
    },
    scripture: {
      reference: 'Mark 9:24',
      text: 'Immediately the boy\'s father exclaimed, "I do believe; help me overcome my unbelief!"',
      version: 'NIV'
    },
    reflection: {
      question: 'What doubts or questions about faith are you wrestling with right now?',
      prompts: [
        'Journal about a time when doubt led to deeper faith',
        'Consider who you can talk to honestly about your questions',
        'Read the stories of biblical figures who doubted'
      ]
    },
    tags: ['faith', 'doubt', 'honesty', 'growth'],
    difficulty: 'intermediate',
    estimatedReadTime: 7,
    publishDate: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000) // 2 days ago
  }
];

const seedContent = async () => {
  try {
    // Connect to MongoDB
    await mongoose.connect(process.env.MONGODB_URI);
    console.log('MongoDB connected...');

    // Clear existing content
    await DailyContent.deleteMany({});
    console.log('Cleared existing content...');

    // Insert sample content
    await DailyContent.insertMany(sampleContent);
    console.log('Sample content inserted successfully!');

    mongoose.connection.close();
    console.log('Database connection closed.');
  } catch (error) {
    console.error('Error seeding database:', error);
    process.exit(1);
  }
};

seedContent();
